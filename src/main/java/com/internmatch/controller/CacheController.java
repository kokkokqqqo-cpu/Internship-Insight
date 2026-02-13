package com.internmatch.controller;

import com.internmatch.cache.SimpleCache;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cache")
public class CacheController {

    @DeleteMapping("/clear")
    public void clear() {
        SimpleCache.getInstance().clear();
    }
}