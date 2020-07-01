package aiPlanning;

import java.util.ArrayList;
import java.util.List;

public class Environment {
	
//	initialization of possible states and actions
	ArrayList<String[]> states = new ArrayList<String[]>(List.of(new String[] { "bulb_red", "frog_down" }, new String[] { "bulb_red", "frog_up" }, new String[] { "bulb_green", "frog_up" }, new String[] { "bulb_green", "frog_down" }, new String[]{ "bulb_blue", "frog_up" }, new String[] { "bulb_blue", "frog_down" }));
	ArrayList<String> actions = new ArrayList<String>(List.of("climb", "switch_red_green", "switch_green_blue", "switch_red_blue"));
	
	ArrayList<String> positions = new ArrayList<String>(List.of("down", "center", "up"));
	ArrayList<String> colors = new ArrayList<String>(List.of("magenta", "blue", "yellow", "white", "light blue"));
	
	public ArrayList<String> getActions(String sourcePosition, String sourceColor, String destinationPosition, String destinationColor) {
		ArrayList<String> actions = new ArrayList<String>();
		
		actions.addAll(findColor(sourceColor, destinationColor));
		actions.addAll(findPosition(sourcePosition, destinationPosition));
		
		return actions;
	}
	
	private ArrayList<String> findColor(String sourceColor, String destinationColor) {
		String colorRight = sourceColor;
		String colorLeft = sourceColor;
		int colorIntRight = colors.indexOf(sourceColor);
		int colorIntLeft = colors.indexOf(sourceColor);
		
		ArrayList<String> actionsRight = new ArrayList<String>();
		ArrayList<String> actionsLeft = new ArrayList<String>();
		
		while (!colorRight.equals(destinationColor) && !colorLeft.equals(destinationColor)) {
			colorIntRight = (colorIntRight + 1) % colors.size();
			colorIntLeft = ((colorIntLeft - 1) % colors.size() + colors.size()) % colors.size();
			colorRight = colors.get(colorIntRight);
			colorLeft = colors.get(colorIntLeft);
			actionsRight.add("right");
			actionsLeft.add("left");
		}
		
		if (colorRight == destinationColor) {
			return actionsRight;
		} else {
			return actionsLeft;
		}
	}
	
	private ArrayList<String> findPosition(String sourcePosition, String destinationPosition) {
		String positionUp = sourcePosition;
		String positionDown = sourcePosition;
		int positionIntUp = positions.indexOf(sourcePosition);
		int positionIntDown = positions.indexOf(sourcePosition);
		
		ArrayList<String> actionsUp = new ArrayList<String>();
		ArrayList<String> actionsDown = new ArrayList<String>();
		
		while (!positionUp.equals(destinationPosition) && !positionDown.equals(destinationPosition)) {
			if (positionIntUp != positions.size() - 1) {
				positionIntUp = positionIntUp + 1;
				actionsUp.add("up");
				positionUp = positions.get(positionIntUp);
			}
			if (positionIntDown != 0) {
				positionIntDown = positionIntDown - 1;
				actionsDown.add("down");
				positionDown = positions.get(positionIntDown);
			}
		}
		
		if (positionUp == destinationPosition) {
			return actionsUp;
		} else {
			return actionsDown;
		}
	}
	
	/**
	 * performs an action on a state of frog
	 * @param state of frog before action
	 * @param action to be performed
	 * @return state of frog after action
	 */
	public String[] getState(String[] state, String action) {
		
//		find index of state and action
		int stateInt = states.indexOf(state);
		int actionInt = actions.indexOf(action);
		
//		call appropriate getState-function
		switch(stateInt) {
			case 0:
				return getState0(actionInt);
			case 1:
				return getState1(actionInt);
			case 2:
				return getState2(actionInt);
			case 3:
				return getState3(actionInt);
			case 4:
				return getState4(actionInt);
			case 5:
				return getState5(actionInt);
			default:
				return state;
		}
	}
	
	private String[] getState0(int action) {
		switch(action) {
			case 0:
				return states.get(1);
			case 1:
				return states.get(2);
			case 3:
				return states.get(4);
			default:
				return states.get(0);
		}
	}
	
	private String[] getState1(int action) {
		switch(action) {
			case 0:
				return states.get(0);
			case 1:
				return states.get(3);
			case 3:
				return states.get(5);
			default:
				return states.get(1);
		}
	}
	
	private String[] getState2(int action) {
		switch(action) {
			case 0:
				return states.get(3);
			case 1:
				return states.get(0);
			case 2:
				return states.get(4);
			default:
				return states.get(2);
		}
	}
	
	private String[] getState3(int action) {
		switch(action) {
			case 0:
				return states.get(2);
			case 1:
				return states.get(1);
			case 2:
				return states.get(5);
			default:
				return states.get(3);
		}
	}
	
	private String[] getState4(int action) {
		switch(action) {
			case 0:
				return states.get(5);
			case 2:
				return states.get(2);
			case 3:
				return states.get(0);
			default:
				return states.get(4);
		}
	}
	
	private String[] getState5(int action) {
		switch(action) {
			case 0:
				return states.get(4);
			case 2:
				return states.get(3);
			case 3:
				return states.get(1);
			default:
				return states.get(5);
		}
	}
}
