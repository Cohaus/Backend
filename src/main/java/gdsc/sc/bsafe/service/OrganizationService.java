package gdsc.sc.bsafe.service;

import gdsc.sc.bsafe.domain.Organization;
import gdsc.sc.bsafe.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Transactional
    public Organization findOrCreateOrganization(String name) {
        return organizationRepository.findByName(name)
                .orElseGet(() -> saveOrganization(name));
    }

    @Transactional
    public Organization saveOrganization(String name) {
        Organization organization = Organization.builder()
                .name(name)
                .build();

        return organizationRepository.save(organization);
    }
}
