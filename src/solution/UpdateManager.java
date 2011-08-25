package solution;

/**
 * @author nubela
 * 
 *         Filters the incoming updates and distribute the updates accordingly
 *         to the relevant parts of the code.
 * 
 */
public class UpdateManager implements TCPListener {

	ControlManager controlManager;

	public UpdateManager(ControlManager controlManager) {
		this.controlManager = controlManager;
	}

	/**
	 * Filters the incoming updates and distribute the updates accordingly to
	 * the relevant parts of the code.
	 * 
	 * @param updateString
	 */
	private void filter(String updateString) {
		String[] updateStringTokens = updateString.split(" ");
		UpdateType updateType = null;
		if (updateStringTokens.length >= 1) {
			updateType = UpdateType.token2UpdateEnum(updateStringTokens[0]);
		}

		if (updateType == UpdateType.FLOOR_POSITION) {
			// notify lift
			Lift lift = controlManager.getLiftById(new Integer(
					updateStringTokens[1]));
			lift.setPosition(new Double(updateStringTokens[2]));
		}

		else if (updateType == UpdateType.LEVEL_REQUEST) {
			// notify control
			controlManager.levelRequest(new Integer(updateStringTokens[1]),
					new Integer(updateStringTokens[2]));
		}

		else if (updateType == UpdateType.IN_LEFT_BUTTON_PRESS) {
			// notify lift
			controlManager.getLiftById(new Integer(updateStringTokens[1]))
					.buttonPress(new Integer(updateStringTokens[2]));
		}

	}

	@Override
	public void update(String updateString) {
		System.out.println("Received update: " + updateString);
		filter(updateString);
	}

	public enum UpdateType {
		FLOOR_POSITION("f"), LEVEL_REQUEST("b"), IN_LEFT_BUTTON_PRESS("p");

		String token;

		UpdateType(String token) {
			this.token = token;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		static UpdateType token2UpdateEnum(String token) {
			for (UpdateType typeOfUpdate : UpdateType.values()) {
				if (typeOfUpdate.getToken().equals(token))
					return typeOfUpdate;
			}
			throw new RuntimeException(
					"Found new update token that is not in enum: " + token);
		}
	}
}
