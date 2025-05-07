package com.doubleo.mobilegateway.grpc;

import com.doubleo.tenantservice.domain.tenant.grpc.HospitalIdToTenantIdRequest;
import com.doubleo.tenantservice.domain.tenant.grpc.HospitalIdToTenantIdResponse;
import com.doubleo.tenantservice.domain.tenant.grpc.HospitalTenantServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HospitalTenantGrpcClient {

    @GrpcClient("tenant-service")
    private HospitalTenantServiceGrpc.HospitalTenantServiceBlockingStub tenantStub;

    public String getTenantIdByHospitalId(Long hospitalId) {
        HospitalIdToTenantIdRequest request =
                HospitalIdToTenantIdRequest.newBuilder().setHospitalId(hospitalId).build();

        HospitalIdToTenantIdResponse response = tenantStub.getTenantIdByHospitalId(request);
        return response.getTenantId();
    }
}
