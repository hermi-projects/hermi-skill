package org.hermi.skill.install.usecase.repository;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.hermi.skill.install.usecase.entity.Skill;
import org.hermi.usecase.commons.validation.Validatable;
import org.hermi.usecase.standard.Repository;

public abstract class FindSkillRepository
    extends Repository<FindSkillRepository.Context, FindSkillRepository.Result> {
  public static record Context(List<String> skillNames) {}

  public static record Result(@NotNull List<Skill> skills) implements Validatable {}
}
