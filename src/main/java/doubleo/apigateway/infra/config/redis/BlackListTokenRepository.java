package com.doubleo.memberservice.domain.auth.repository;

import com.doubleo.memberservice.domain.auth.domain.BlackListToken;
import org.springframework.data.repository.CrudRepository;

public interface BlackListTokenRepository extends CrudRepository<BlackListToken, String> {}
