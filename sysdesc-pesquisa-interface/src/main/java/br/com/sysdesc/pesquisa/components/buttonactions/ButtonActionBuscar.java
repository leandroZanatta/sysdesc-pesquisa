package br.com.sysdesc.pesquisa.components.buttonactions;

import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import static java.awt.event.KeyEvent.VK_L;

public class ButtonActionBuscar extends ButtonAction {

	private static final long serialVersionUID = 1L;

	public ButtonActionBuscar() {

		super("mapBuscar", VK_L, CTRL_DOWN_MASK, "search.png", "Pesquisar");
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
