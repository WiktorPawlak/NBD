package p.lodz.pl.nbd.test;

import static p.lodz.pl.nbd.manager.mapper.BoxMapper.toBundle;
import static p.lodz.pl.nbd.manager.mapper.BoxMapper.toBundleType;
import static p.lodz.pl.nbd.manager.mapper.BoxMapper.toEnvelope;
import static p.lodz.pl.nbd.manager.mapper.BoxMapper.toEnvelopeType;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import p.lodz.pl.nbd.BoxesShipmentsFixture;
import p.lodz.pl.nbd.model.box.Bundle;
import p.lodz.pl.nbd.model.box.Envelope;
import p.lodz.pl.nbd.persistence.udt.box.BoxUdt;
import p.lodz.pl.nbd.persistence.udt.box.BundleUdt;
import p.lodz.pl.nbd.persistence.udt.box.EnvelopeUdt;

class BoxUdtMapperTest {

    private BoxesShipmentsFixture fixture;

    @BeforeEach
    void init() {
        fixture = new BoxesShipmentsFixture();
    }

    @Test
    void boxDocumentEnvelopeMapsToBoxEnvelopeSuccessfully() {
        //given
        EnvelopeUdt envelopeDoc = fixture.envelopeDoc;

        //when
        Envelope mappedEnvelope = toEnvelope(envelopeDoc);

        //then
        Assertions.assertThat(mappedEnvelope).usingRecursiveComparison().isEqualTo(envelopeDoc);
    }

    @Test
    void boxEnvelopeMapsToBoxDocumentEnvelopeSuccessfully() {
        //given
        Envelope envelope = fixture.envelope;

        //when
        EnvelopeUdt mappedEnvelopeDoc = toEnvelopeType(envelope);

        //then
        Assertions.assertThat(mappedEnvelopeDoc).usingRecursiveComparison().isEqualTo(envelope);
    }

    @Test
    void boxDocumentBundleMapsToBoxBundleSuccessfully() {
        //given
        BundleUdt bundleDoc = fixture.bundleDoc;

        //when
        Bundle mappedBundle = toBundle(bundleDoc);

        //then
        Assertions.assertThat(mappedBundle).usingRecursiveComparison().isEqualTo(bundleDoc);
    }

    @Test
    void boxBundleMapsToBoxDocumentBundleSuccessfully() {
        //given
        Bundle bundle = fixture.bundle;

        //when
        BoxUdt mappedBundleDoc = toBundleType(bundle);

        //then
        Assertions.assertThat(mappedBundleDoc).usingRecursiveComparison().isEqualTo(bundle);
    }
}
