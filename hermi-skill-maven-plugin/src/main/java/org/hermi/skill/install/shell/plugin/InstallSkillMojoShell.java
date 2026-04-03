package org.hermi.skill.install.shell.plugin;

import java.util.List;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.hermi.skill.install.shell.repository.JarFindSkillRepository;
import org.hermi.skill.install.usecase.DefaultInstallSkillUseCase;
import org.hermi.skill.install.usecase.InstallSkillUseCase;

@Mojo(name = "install")
public class InstallSkillMojoShell extends AbstractMojo {
  @Parameter(defaultValue = "${project}", readonly = true)
  private MavenProject project;

  @Parameter(property = "agent", defaultValue = "agent")
  private String agent;

  @Parameter(property = "skills")
  private List<String> skills;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    if (!this.project.isExecutionRoot()) {
      getLog().info("Skipping non-execution root project.");
      return;
    }

    getLog().info("Installing skills...");
    try {
      List<String> names = this.skills != null ? this.skills : List.of();
      var installSkillUseCaseCommand =
          new InstallSkillUseCase.Command(this.project.getBasedir(), this.agent, names);
      InstallSkillUseCase useCase = new DefaultInstallSkillUseCase(new JarFindSkillRepository());
      var response = useCase.execute(installSkillUseCaseCommand);
      if (response.failedSkills().isEmpty()) {
        getLog().info("Installed skills: " + response.installedSkills());
      } else {
        getLog().info("Failed to install skills: " + response.failedSkills());
      }
    } catch (Exception e) {
      throw new MojoExecutionException("Failed to install skills", e);
    }
  }
}
