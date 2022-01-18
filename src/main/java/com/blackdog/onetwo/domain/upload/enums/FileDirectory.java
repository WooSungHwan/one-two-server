package com.blackdog.onetwo.domain.upload.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileDirectory {

    IMAGE("image"),
    PROFILE("profile"),
    FILE("file");

    private String directory;

    public String getDirectoryUrl(String key) {
        return String.format("%s/%s", this.directory, key);
    }
}
