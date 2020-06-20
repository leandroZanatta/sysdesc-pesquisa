package br.com.sysdesc.pesquisa.components.buttonactions;

import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import static java.awt.event.KeyEvent.VK_N;

public class ButtonActionNovo extends ButtonAction {

	private static final long serialVersionUID = 1L;

	public ButtonActionNovo() {

		super("mapNovo", VK_N, CTRL_DOWN_MASK, "new.png", "Novo");
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
		setEnabled(Boolean.FALSE);
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
