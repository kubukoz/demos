package com.kubukoz;

import alloy.SimpleRestJsonTrait;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.validation.AbstractValidator;
import software.amazon.smithy.model.validation.ValidationEvent;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ApiVersioningValidator extends AbstractValidator {

    @Override
    public List<ValidationEvent> validate(Model model) {
        return model.getShapesWithTrait(SimpleRestJsonTrait.class).stream()
                .flatMap(restJson -> restJson.asServiceShape().stream())
                .flatMap(shape -> model.getShape(shape.getId()).stream()).flatMap(shape -> {
                    Optional<ApiVersionTrait> maybeVersion = shape.getTrait(ApiVersionTrait.class);
                    if (maybeVersion.isEmpty()) {
                        return Stream.of(
                                error(shape, "Missing @apiVersion trait on service " + shape.getId() + ". Found traits:\n\n=======================\n" + showTraits(shape))
                        );
                    } else {
                        return Stream.<ValidationEvent>empty();
                    }
                }).collect(Collectors.toList());
    }

    private String showTraits(Shape shape) {
        return shape.getAllTraits().entrySet().stream().map(e -> e.getKey().toString() + ": " + e.getValue().getClass().getName()).collect(Collectors.joining("\n")) + "\n=======================\n";
    }
}
