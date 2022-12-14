package p.lodz.pl.nbd.persistence.document.box;

import java.util.UUID;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.mapper.entity.EntityHelper;

public class CreateBoxQueryProvider {

    private final CqlSession session;

    private final PreparedStatement createBox;

    CreateBoxQueryProvider(
            MapperContext mapperContext,
            EntityHelper<BoxDocument> boxDocumentEntityHelper) {
        this.session = mapperContext.getSession();

        createBox = session.prepare(boxDocumentEntityHelper.insert().asCql());
    }

    void create(BoxDocument box) {
        if (box.getId() == null) {
            box.setId(UUID.randomUUID());
        }

        session.execute(createBox.bind(box));
    }
}
