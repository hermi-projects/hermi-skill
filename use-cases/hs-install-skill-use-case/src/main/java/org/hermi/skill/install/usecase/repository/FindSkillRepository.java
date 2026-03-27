package org.hermi.skill.install.usecase.repository;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.hermi.commons.validation.Validatable;
import org.hermi.usecase.Repository;

public abstract class FindSkillRepository
    extends Repository<FindSkillRepository.Command, FindSkillRepository.Result> {
  public static record Command(List<String> skillNames) {}

  public static record Result(@NotNull List<Skill> skills) implements Validatable {}
}
