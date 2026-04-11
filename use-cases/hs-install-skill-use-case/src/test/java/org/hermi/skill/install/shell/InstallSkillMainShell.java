package org.hermi.skill.install.shell;

import java.util.List;
import org.hermi.skill.install.usecase.DefaultInstallSkillUseCase;
import org.hermi.skill.install.usecase.InstallSkillUseCase;

public class InstallSkillMainShell {
  public static void main(String[] args) {
    InstallSkillUseCase useCase = new DefaultInstallSkillUseCase();
    InstallSkillUseCase.Context context = new InstallSkillUseCase.Context(null, "agent", List.of());
    useCase.execute(context);
    System.out.println("Hello World");
  }
}
