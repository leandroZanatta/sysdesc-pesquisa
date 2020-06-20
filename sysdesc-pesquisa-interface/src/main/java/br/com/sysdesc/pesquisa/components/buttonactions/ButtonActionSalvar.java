package br.com.sysdesc.pesquisa.components.buttonactions;

import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import static java.awt.event.KeyEvent.VK_S;

public class ButtonActionSalvar extends ButtonAction {

	private static final long serialVersionUID = 1L;

	public ButtonActionSalvar() {

		super("mapSalvar", VK_S, CTRL_DOWN_MASK, "save.png", "Salvar");
	}

	@Override
	public void saveEvent() {
		setEnabled(Boolean.FALSE);
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
		setEnabled(Boolean.FALSE);
	}

	@Override
	public void startEvent() {
		setEnabled(Boolean.FALSE);
	}
}
