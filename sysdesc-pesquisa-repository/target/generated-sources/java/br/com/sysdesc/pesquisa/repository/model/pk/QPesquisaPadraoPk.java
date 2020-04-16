package br.com.sysdesc.pesquisa.repository.model.pk;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPesquisaPadraoPk is a Querydsl query type for PesquisaPadraoPk
 */
@Generated("com.mysema.query.codegen.EmbeddableSerializer")
public class QPesquisaPadraoPk extends BeanPath<PesquisaPadraoPk> {

    private static final long serialVersionUID = 672676581L;

    public static final QPesquisaPadraoPk pesquisaPadraoPk = new QPesquisaPadraoPk("pesquisaPadraoPk");

    public final NumberPath<Long> codigoPesquisa = createNumber("codigoPesquisa", Long.class);

    public final NumberPath<Long> codigoUsuario = createNumber("codigoUsuario", Long.class);

    public QPesquisaPadraoPk(String variable) {
        super(PesquisaPadraoPk.class, forVariable(variable));
    }

    public QPesquisaPadraoPk(Path<? extends PesquisaPadraoPk> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPesquisaPadraoPk(PathMetadata<?> metadata) {
        super(PesquisaPadraoPk.class, metadata);
    }

}

