package com.github.utilities;

import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GlobalDataUtils {

    private String repoName;
    private Response response;

}
