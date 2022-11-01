package p.lodz.pl.nbd.test;

import static p.lodz.pl.nbd.manager.mapper.BoxMapper.toBox;
import static p.lodz.pl.nbd.manager.mapper.BoxMapper.toBoxDocument;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import p.lodz.pl.nbd.BoxesLockersFixture;
import p.lodz.pl.nbd.model.box.Box;
import p.lodz.pl.nbd.persistance.document.box.BoxDocument;

class BoxMapperTest {

    private BoxesLockersFixture fixture;

    @BeforeEach
    void init() {
        fixture = new BoxesLockersFixture();
    }

    @Test
    void boxDocumentEnvelopeMapsToBoxEnvelopeSuccessfully() {
        //given
        BoxDocument envelopeDoc = fixture.envelopeDoc;

        //when
        Box mappedEnvelope = toBox(envelopeDoc);

        //then
        Assertions.assertThat(mappedEnvelope).usingRecursiveComparison().isEqualTo(envelopeDoc);
    }

    @Test
    void boxEnvelopeMapsToBoxDocumentEnvelopeSuccessfully() {
        //given
        Box envelope = fixture.envelope;

        //when
        BoxDocument mappedEnvelopeDoc = toBoxDocument(envelope);

        //then
        Assertions.assertThat(mappedEnvelopeDoc).usingRecursiveComparison().isEqualTo(envelope);
    }

    @Test
    void boxDocumentBundleMapsToBoxBundleSuccessfully() {
        //given
        BoxDocument bundleDoc = fixture.bundleDoc;

        //when
        Box mappedBundle = toBox(bundleDoc);

        //then
        Assertions.assertThat(mappedBundle).usingRecursiveComparison().isEqualTo(bundleDoc);
    }

    @Test
    void boxBundleMapsToBoxDocumentBundleSuccessfully() {
        //given
        Box bundle = fixture.bundle;

        //when
        BoxDocument mappedBundleDoc = toBoxDocument(bundle);

        //then
        Assertions.assertThat(mappedBundleDoc).usingRecursiveComparison().isEqualTo(bundle);
    }
}
