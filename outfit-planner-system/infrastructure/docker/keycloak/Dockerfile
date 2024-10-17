FROM registry.access.redhat.com/ubi9 AS ubi-micro-build
RUN mkdir -p /mnt/rootfs
RUN dnf install --installroot /mnt/rootfs curl --releasever 9 --setopt install_weak_deps=false --nodocs -y && \
    dnf --installroot /mnt/rootfs clean all && \
    rpm --root /mnt/rootfs -e --nodeps setup

FROM quay.io/keycloak/keycloak:26.0.1
RUN mkdir -p /opt/keycloak/data/import
COPY --from=ubi-micro-build /mnt/rootfs /
COPY realm-export.json /opt/keycloak/data/import/outfit-planner-config.json