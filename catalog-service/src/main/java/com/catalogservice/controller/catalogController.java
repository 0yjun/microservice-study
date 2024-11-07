package com.catalogservice.controller;

import com.catalogservice.dto.ResponseCatalog;
import com.catalogservice.entity.CatalogEntity;
import com.catalogservice.service.CatalogService;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/catalog-service")
public class catalogController {
    private final Environment env;
    private final CatalogService catalogService;


    public catalogController(
            Environment env,
            CatalogService catalogService
    ) {
        this.env = env;
        this.catalogService = catalogService;
    }

    @GetMapping("/health-check")
    public String status(){
        return String.format("port load on %s", env.getProperty("local.server.port"));
    }

    @GetMapping("catalogs")
    public ResponseEntity<List<ResponseCatalog>> getCatalogs(){
        Iterable<CatalogEntity> catalogList = catalogService.getAllCatalogs();
        List<ResponseCatalog> result = new ArrayList<ResponseCatalog>();
        catalogList.forEach(v->{
            result.add(new ModelMapper().map(v, ResponseCatalog.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
