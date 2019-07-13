package org.bioauth.typeauth.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Component;
import springfox.documentation.service.GrantType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Entity
@Data
@NoArgsConstructor
//@Table(name = "client")
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

	@NotNull
	private Integer accessTokenValiditySeconds;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			joinColumns = @JoinColumn(name = "CLIENT_ID"),
			inverseJoinColumns = @JoinColumn(name = "SCOPE_ID")
	)
	private Set<Scope> scopes = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			joinColumns = @JoinColumn(name = "CLIENT_ID"),
			inverseJoinColumns = @JoinColumn(name = "AUTH_GRANT_TYPE_ID")
	)
	private Set<AuthGrantType> authGrantTypes = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			joinColumns = @JoinColumn(name = "CLIENT_ID"),
			inverseJoinColumns = @JoinColumn(name = "GRANTED_AUTHORITY_CLIENT_ID")
	)
	private Set<GrantedAuthorityClient> grantedAuthorities = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			joinColumns = @JoinColumn(name = "CLIENT_ID"),
			inverseJoinColumns = @JoinColumn(name = "RESOURCE_ID")
	)
	private Set<ResourceId> resIds = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(
			joinColumns = @JoinColumn(name = "CLIENT_ID"),
			inverseJoinColumns = @JoinColumn(name = "PERSON_ID")
	)
	private List<Person> persons = new ArrayList<>();

	public Client(@NotEmpty String clientId, @NotEmpty @Length(min = 4) String clientSecret, Set<Scope> scopes, Set<AuthGrantType> authGrantTypes, @NotNull Integer accessTokenValiditySeconds, Set<GrantedAuthorityClient> grantedAuthorities, Set<ResourceId> resIds) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.scopes = scopes;
		this.authGrantTypes = authGrantTypes;
		this.accessTokenValiditySeconds = accessTokenValiditySeconds;
		this.grantedAuthorities = grantedAuthorities;
		this.resIds = resIds;
	}

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

	public Person personExist(String personName)
	{
		int index = persons.stream()
				.map(Person::getName)
				.collect(Collectors.toList()
				)
				.indexOf(personName);
		if (index > -1)
			return persons.get(index);
		else
			return null;
	}
}