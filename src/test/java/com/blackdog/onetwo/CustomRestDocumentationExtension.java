package com.blackdog.onetwo;

import org.springframework.restdocs.RestDocumentationExtension;

public class CustomRestDocumentationExtension extends RestDocumentationExtension {

    public static final String SNIPPET_OUTPUT_DIRECTORY = "src/test/resources/api/v1/snippet";

    public CustomRestDocumentationExtension() {
        super(SNIPPET_OUTPUT_DIRECTORY);
    }

}
