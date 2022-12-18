package com.axial.modules.openapi_manager.model.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiConfig {

    /**
     * API özelleştirmeleri için herhangi bir noktada bu API'nin hangi API olduğu anlamak için kullanılacak eşsiz tanımlayıcı.
     * Her zaman set edilmeli
     */
    private String apiId;

    private String name;

    private String groupName;

    private String path;

    private Map<String, SecurityHeaderConfig> securityHeaders;

    private Map<String, HeaderConfig> headers;

    private List<String> allowedRoles;

    private String description;

    /**
     * Bu flag sadece apiId set etme işlemi sırasında kullanılacak bir flag olur başka bir iş için kullanılmamalıdır.
     */
    private Boolean pointerFlag;

}