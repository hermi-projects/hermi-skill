package org.hermi.skill.install.shell.repository;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.hermi.skill.install.usecase.repository.FindSkillRepository;
import org.hermi.skill.install.usecase.repository.Skill;

public class JarFindSkillRepository extends FindSkillRepository {

  @Override
  protected Result doSend(Command command) {
    URL resource = getClass().getClassLoader().getResource("skills");
    if (resource == null || !"jar".equals(resource.getProtocol())) {
      return new Result(List.of());
    }
    try {
      List<String> requestedNames = command.skillNames();
      return new Result(readFromJar(resource, requestedNames));
    } catch (IOException e) {
      throw new RuntimeException("Failed to read skills from jar", e);
    }
  }

  private List<Skill> readFromJar(URL resource, List<String> skillNames) throws IOException {
    List<Skill> skills = new ArrayList<>();
    String jarPath = resource.getPath().substring(5, resource.getPath().indexOf("!"));
    try (JarFile jar = new JarFile(jarPath)) {
      Enumeration<JarEntry> entries = jar.entries();

      while (entries.hasMoreElements()) {
        JarEntry entry = entries.nextElement();
        if (!entry.getName().startsWith("skills/") || entry.getName().equals("skills/")) {
          continue;
        }

        String relativeName = entry.getName().substring("skills/".length());
        String skillName = relativeName.split("/")[0];

        if (!skillNames.isEmpty() && !skillNames.contains(skillName)) {
          continue;
        }

        if (!entry.isDirectory()) {
          try (InputStream in = jar.getInputStream(entry)) {
            skills.add(new Skill(skillName, relativeName, in.readAllBytes()));
          }
        }
      }
    }
    return skills;
  }
}
