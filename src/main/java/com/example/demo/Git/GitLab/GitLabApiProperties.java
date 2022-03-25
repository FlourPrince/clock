
package com.example.demo.Git.GitLab;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */


public class GitLabApiProperties {

    private String hostUrl;

    private String privateToken;

    @Override
    public String toString() {
        return "GitLabApiProperties{" +
                "hostUrl='" + hostUrl + '\'' +
                ", privateToken='" + privateToken + '\'' +
                '}';
    }
}

