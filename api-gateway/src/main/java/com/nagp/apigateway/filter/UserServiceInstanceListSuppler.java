package com.nagp.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Flux;

import java.util.List;

class UserServiceInstanceListSuppler  implements ServiceInstanceListSupplier {

    @Autowired
    private DiscoveryClient discoveryClient;
    private final String serviceId;

    UserServiceInstanceListSuppler(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String getServiceId() {
        return serviceId;
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        return Flux.just(this.discoveryClient.getInstances(getServiceId()));
    }
}
