package org.hermi.skill.install.usecase;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.File;
import java.util.List;
import org.hermi.commons.validation.Validatable;
import org.hermi.usecase.UseCase;

public abstract class InstallSkillUseCase
    extends UseCase<InstallSkillUseCase.Command, InstallSkillUseCase.Result> {

  public static record Command(
      @NotNull File baseDir,
      @NotNull @NotBlank String agent,
      @NotNull @NotEmpty List<String> skills)
      implements Validatable {}

  public static record Result(
      @NotNull @NotEmpty List<String> installedSkills,
      @NotNull @NotEmpty List<String> failedSkills) {}
}
