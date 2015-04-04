package by.parfen.disptaxi.datamodel.enums;

public enum AppRoleId {

	ADMIN_ROLE(0), OPERATOR_ROLE(1), DRIVER_ROLE(2), CUSTOMER_ROLE(3);

	private final int value;

	private AppRoleId(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public Long getLongValue() {
		return Long.valueOf(this.value);
	}

	@Override
	public String toString() {
		String stringRole = "";
		switch (this) {
		case ADMIN_ROLE:
			stringRole = "ADMIN_ROLE";
			break;
		case OPERATOR_ROLE:
			stringRole = "OPERATOR_ROLE";
			break;
		case DRIVER_ROLE:
			stringRole = "DRIVER_ROLE";
			break;
		case CUSTOMER_ROLE:
			stringRole = "CUSTOMER_ROLE";
		}
		return stringRole;
	}
}
