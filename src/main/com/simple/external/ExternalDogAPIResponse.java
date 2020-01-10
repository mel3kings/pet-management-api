package com.simple.external;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalDogAPIResponse {
    private String message;
    private String status;
    private String fileName;
    private String dogName;
}
