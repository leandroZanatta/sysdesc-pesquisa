package br.com.sysdesc.pesquisa.components.buttonactions;

import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import static java.awt.event.KeyEvent.VK_E;

public class ButtonActionEditar extends ButtonAction {

	private static final long serialVersionUID = 1L;

	public ButtonActionEditar() {

		super("mapEditar", VK_E, CTRL_DOWN_MASK, "edit.png", "Editar");
	}

	@Override
	public void saveEvent() {
		setEnabled(Boolean.FALSE);
	}

	@Override
	public void editEvent() {
		setEnabled(Boolean.FALSE);
	}

	@Override
	public void newEvent() {
		setEnabled(Boolean.FALSE);
	}

	@Override
	public void searchEvent() {
		setEnabled(Boolean.TRUE);
	}

	@Override
	public void startEvent() {
		setEnabled(Boolean.FALSE);
	}

}
