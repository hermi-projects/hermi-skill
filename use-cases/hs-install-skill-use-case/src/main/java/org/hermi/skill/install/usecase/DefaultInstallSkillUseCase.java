package org.hermi.skill.install.usecase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.hermi.skill.install.usecase.entity.Skill;
import org.hermi.skill.install.usecase.repository.FindSkillRepository;

public class DefaultInstallSkillUseCase extends InstallSkillUseCase {

  private final List<FindSkillRepository> installSkillRepositories;

  public DefaultInstallSkillUseCase(List<FindSkillRepository> installSkillRepositories) {
    this.installSkillRepositories = List.copyOf(installSkillRepositories);
  }

  public DefaultInstallSkillUseCase(FindSkillRepository... installSkillRepositories) {
    this(List.of(installSkillRepositories));
  }

  @Override
  protected Result doExecute(Command command) {
    return installSkills(command);
  }

  protected Result installSkills(Command command) {
    List<String> installed = new ArrayList<>();
    List<String> failed = new ArrayList<>();
    Path targetDir = command.baseDir().toPath().resolve("." + command.agent() + "/skills");
    for (FindSkillRepository repository : this.installSkillRepositories) {

      var findSkillCommand = new FindSkillRepository.Command(command.skills());
      var response = repository.send(findSkillCommand);
      for (Skill skill : response.skills()) {
        try {
          Path targetPath = targetDir.resolve(skill.relativePath());
          Files.createDirectories(targetPath.getParent());
          Files.write(targetPath, skill.content());

          if (!installed.contains(skill.name())) {
            installed.add(skill.name());
          }
        } catch (IOException e) {
          throw new RuntimeException("Failed to install skill: " + skill.name(), e);
        }
      }
    }

    return new Result(installed, failed);
  }
}
