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

		if (updateType == UpdateType.FloorPosition) {
			Lift lift = controlManager.getLiftById(new Integer(
					updateStringTokens[1]));
			lift.setPosition(new Double(updateStringTokens[2]));
			System.out.println("works");
		}

	}

	@Override
	public void update(String updateString) {
		System.out.println("Received update: " + updateString);
		filter(updateString);
	}

	public enum UpdateType {
		FloorPosition("f");

		String token;

		UpdateType(String token) {
			this.token = token;
		}

		static UpdateType token2UpdateEnum(String token) {
			for (UpdateType typeOfUpdate : UpdateType.values())
				return typeOfUpdate;
			throw new RuntimeException(
					"Found new update token that is not in enum: " + token);
		}
	}
}
