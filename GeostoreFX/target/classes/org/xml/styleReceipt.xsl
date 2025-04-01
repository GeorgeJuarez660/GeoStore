<?xml version="1.0"?>
<!--Stile dello scontrino GeoStore per l'ordinazione prodotto-->
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:template match="/">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="scontrino">
                    <fo:region-body />
                </fo:simple-page-master>
            </fo:layout-master-set> <!--Formato del pdf-->

            <fo:page-sequence master-reference="scontrino">
                <fo:flow flow-name="xsl-region-body"> <!--Stile del contenuto-->

                    <!--Creazione della tabella per contenere prodotto, quantità e prezzo unitario-->
                    <fo:table border="none" width="100%">
                        <fo:table-column column-width="50%"/>
                        <fo:table-column column-width="25%"/>
                        <fo:table-column column-width="25%"/>

                        <fo:table-header>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block font-weight="bold">
                                        <xsl:value-of select="Corpo/TitoloTabella/colonnaT1"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block font-weight="bold">
                                        <xsl:value-of select="Corpo/TitoloTabella/colonnaT2"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block font-weight="bold">
                                        <xsl:value-of select="Corpo/TitoloTabella/colonnaT3"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-header>

                        <fo:table-body>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block>
                                        <xsl:value-of select="Corpo/CorpoTabella/colonnaC1"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block>
                                        <xsl:value-of select="Corpo/CorpoTabella/colonnaC2"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block>
                                        <xsl:value-of select="Corpo/CorpoTabella/colonnaC3"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-body>
                    </fo:table>

                    <!--Creazione della semitabella per conservare il titolo totale e il suo valore-->
                    <fo:table border="none" width="100%">
                        <fo:table-column column-width="50%"/>
                        <fo:table-column column-width="50%"/>

                        <fo:table-header>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block font-weight="bold">
                                        <xsl:value-of select="PiedeTotale/colonnaPT"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block font-weight="bold">
                                        <xsl:value-of select="PiedeTotale/colonnaPC"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-header>
                    </fo:table>

                    <!--Creazione della tabella avente il numero documento con data e ora e il nome cliente-->
                    <fo:table border="none" width="100%">
                        <fo:table-column column-width="50%"/>
                        <fo:table-column column-width="50%"/>

                        <fo:table-header>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block font-weight="bold">
                                        <xsl:value-of select="Didascalia/DidascaliaTitolo/colonnaDT"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block font-weight="bold">
                                        <xsl:value-of select="Didascalia/DidascaliaTitolo/colonnaNT"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-header>

                        <fo:table-body>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block font-weight="bold">
                                        <xsl:value-of select="Didascalia/DidascaliaCorpo/colonnaDC"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block font-weight="bold">
                                        <xsl:value-of select="Didascalia/DidascaliaCorpo/colonnaNC"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-body>
                    </fo:table>

                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>