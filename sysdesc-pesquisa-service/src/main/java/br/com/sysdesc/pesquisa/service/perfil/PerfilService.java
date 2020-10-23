package br.com.sysdesc.pesquisa.service.perfil;

import br.com.sysdesc.pesquisa.repository.dao.impl.PerfilDAO;
import br.com.sysdesc.pesquisa.repository.model.Perfil;
import br.com.sysdesc.pesquisa.service.impl.AbstractPesquisableServiceImpl;
import br.com.sysdesc.util.classes.StringUtil;
import br.com.sysdesc.util.constants.MensagemUtilConstants;
import br.com.sysdesc.util.exception.SysDescException;

public class PerfilService extends AbstractPesquisableServiceImpl<Perfil> {

	public PerfilService() {
		super(new PerfilDAO(), Perfil::getIdPerfil);
	}

	@Override
	public void validar(Perfil objetoPersistir) {

		if (StringUtil.isNullOrEmpty(objetoPersistir.getDescricao())) {

			throw new SysDescException(MensagemUtilConstants.MENSAGEM_INSIRA_DESCRICAO_VALIDA);
		}
	}
}
