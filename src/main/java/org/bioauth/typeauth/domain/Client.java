package org.bioauth.typeauth.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import springfox.documentation.service.GrantType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
public class Client implements ClientDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Column(unique = true)
	private String clientId;

	@NotEmpty
	@Length(min = 4)
	private String clientSecret;

	@ManyToMany
	@JoinTable(
			joinColumns = @JoinColumn(name = "CLIENT_ID"),
			inverseJoinColumns = @JoinColumn(name = "SCOPE_ID")
	)
	private Set<Scope> scopes;

	@ManyToMany
	@JoinTable(
			joinColumns = @JoinColumn(name = "CLIENT_ID"),
			inverseJoinColumns = @JoinColumn(name = "AUTH_GRANT_TYPE_ID")
	)
	private Set<AuthGrantType> authGrantTypes;

	@NotNull
	private Integer accessTokenValiditySeconds;

	@ManyToMany
	@JoinTable(
			joinColumns = @JoinColumn(name = "CLIENT_ID"),
			inverseJoinColumns = @JoinColumn(name = "GRANTED_AUTHORITY_CLIENT_ID")
	)
	private Set<GrantedAuthorityClient> grantedAuthorities;

	@ManyToMany
	@JoinTable(
			joinColumns = @JoinColumn(name = "CLIENT_ID"),
			inverseJoinColumns = @JoinColumn(name = "RESOURCE_ID")
	)
	private Set<ResourceId> resIds;

	@Override
	public boolean isSecretRequired() {
		return true;
	}

	@Override
	public boolean isScoped() {
		return true;
	}

	@Override
	public Set<String> getRegisteredRedirectUri()
	{
		return null;
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return null;
	}

	@Override
	public boolean isAutoApprove(String scope) {
		return false;
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {
		return null;
	}

	@Override
	public Set<String> getScope() {
		return scopes.stream()
				.map(scope -> scope.getScope())
				.collect(Collectors.toSet())
				;
	}

	@Override
	public Set<String> getAuthorizedGrantTypes() {
		return authGrantTypes.stream()
				.map(authGrantType ->  authGrantType.getType())
				.collect(Collectors.toSet())
				;
	}

	@Override
	public Set<String> getResourceIds() {
		return resIds.stream()
				.map(resourceId -> resourceId.getResourceId())
				.collect(Collectors.toSet())
				;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return Collections.emptySet();
	}
}