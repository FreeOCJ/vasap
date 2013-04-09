package ch.jesc.vasap.security;

public class SecurityDetails {

	private long userId;
	private String principal;
	private String first;
	private String name;


	public SecurityDetails(long userId, String principal, String name, String first) {
		this.userId = userId;
		this.principal = principal;
		this.name = name;
		this.first = first;
	}

	public long getUserId() {
		return userId;
	}

	public String getPrincipal() {
		return principal;
	}

	public String getFirst() {
		return first;
	}

	public String getName() {
		return name;
	}
}
