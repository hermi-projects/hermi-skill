package org.hermi.skill.install.usecase.entity;

import jakarta.validation.constraints.NotNull;

public record Skill(@NotNull String name, @NotNull String relativePath, @NotNull byte[] content) {}
