package br.com.sysdesc.pesquisa.components.buttonactions;

import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import static java.awt.event.KeyEvent.VK_PAGE_DOWN;

public class ButtonActionUltimo extends ButtonAction {

	private static final long serialVersionUID = 1L;

	public ButtonActionUltimo() {

		super("mapUltimo", VK_PAGE_DOWN, CTRL_DOWN_MASK, "last.png", "Último");
	}

	@Override
	public void saveEvent() {
		setEnabled(Boolean.TRUE);
	}

	@Override
	public void editEvent() {
		setEnabled(Boolean.TRUE);
	}

	@Override
	public void newEvent() {
		setEnabled(Boolean.TRUE);
	}

	@Override
	public void searchEvent() {
		setEnabled(Boolean.TRUE);
	}

	@Override
	public void startEvent() {
		setEnabled(Boolean.TRUE);
	}

}
