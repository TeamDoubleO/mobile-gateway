package com.doubleo.apigateway.infra.config.redis;


import org.springframework.data.repository.CrudRepository;

public interface BlackListTokenRepository extends CrudRepository<BlackListToken, String> {}
