<?xml version="1.0"?>
<!--Stile dello scontrino GeoStore per l'ordinazione prodotto-->
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:template match="/">

        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="scontrino"
                                       margin-top="1cm"
                                       margin-bottom="1cm"
                                       margin-left="1cm"
                                       margin-right="1cm">
                    <fo:region-body />
                </fo:simple-page-master>
            </fo:layout-master-set> <!--Formato del pdf-->

            <fo:page-sequence master-reference="scontrino">
                <fo:flow flow-name="xsl-region-body"> <!--Stile del contenuto-->

                    <fo:block-container width="100%" height="20mm"
                                        background-color="#a4a4a4" margin-top="2%">
                        <fo:block text-align="center" color="black" font-size="12pt">
                        </fo:block>
                    </fo:block-container> <!--Mi creo un rettangolo color grigio-->

                    <!--Inserisco lo stile nei titoli principali-->
                    <fo:block font-family="Arial, Helvetica, sans-serif" font-size="16pt" font-weight="bold" text-align="center" margin-top="2%">
                        <xsl:value-of select="Scontrino/Intestazione/nomeNegozio"/>
                    </fo:block>
                    <fo:block font-family="Arial, Helvetica, sans-serif" font-size="16pt" font-weight="normal" text-align="center" margin-top="2%">
                        <xsl:value-of select="Scontrino/Intestazione/via"/>
                    </fo:block>

                    <!--Creazione della tabella per contenere prodotto, quantità e prezzo unitario-->
                    <fo:table table-layout="fixed" border-width="none" width="100%" margin-top="2%">
                        <fo:table-column column-width="50%"/>
                        <fo:table-column column-width="25%"/>
                        <fo:table-column column-width="25%"/>

                        <fo:table-header border-bottom="2pt dashed black">
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block font-family="Arial, Helvetica, sans-serif" font-size="16pt" font-weight="bold" text-align="left">
                                        <xsl:value-of select="Scontrino/Corpo/TitoloTabella/colonnaT1"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block font-family="Arial, Helvetica, sans-serif" font-size="16pt" font-weight="bold" text-align="center">
                                        <xsl:value-of select="Scontrino/Corpo/TitoloTabella/colonnaT2"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block font-family="Arial, Helvetica, sans-serif" font-size="16pt" font-weight="bold" text-align="right">
                                        <xsl:value-of select="Scontrino/Corpo/TitoloTabella/colonnaT3"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-header>

                        <fo:table-body>
                            <fo:table-row height="7mm"> <!-- Riga vuota per creare spazio -->
                                <fo:table-cell>
                                    <fo:block/>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block font-family="Arial, Helvetica, sans-serif" font-size="16pt" font-weight="normal" text-align="left">
                                        <xsl:value-of select="Scontrino/Corpo/CorpoTabella/colonnaC1"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block font-family="Arial, Helvetica, sans-serif" font-size="16pt" font-weight="normal" text-align="center">
                                        <xsl:value-of select="Scontrino/Corpo/CorpoTabella/colonnaC2"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block font-family="Arial, Helvetica, sans-serif" font-size="16pt" font-weight="normal" text-align="right">
                                        <xsl:value-of select="Scontrino/Corpo/CorpoTabella/colonnaC3"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-body>
                    </fo:table>

                    <fo:block border-bottom="2pt dashed black" margin-top="65%"><!--Inserisco la linea tratteggiata (dashed)-->
                        <!-- Questa linea separa il contenuto -->
                    </fo:block>

                    <!--Creazione della semitabella per conservare il titolo totale e il suo valore-->
                    <fo:table table-layout="fixed" border-width="none" width="100%" margin-top="2%">
                        <fo:table-column column-width="50%"/>
                        <fo:table-column column-width="50%"/>

                        <fo:table-body>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block font-family="Arial, Helvetica, sans-serif" font-size="16pt" font-weight="bold" text-align="left">
                                        <xsl:value-of select="Scontrino/PiedeTotale/colonnaPT"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block font-family="Arial, Helvetica, sans-serif" font-size="16pt" font-weight="bold" text-align="right">
                                        <xsl:value-of select="Scontrino/PiedeTotale/colonnaPC"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-body>
                    </fo:table>

                    <fo:block border-bottom="2pt dashed black" margin-top="2%"><!--Inserisco la linea tratteggiata (dashed)-->
                        <!-- Questa linea separa il contenuto -->
                    </fo:block>

                    <!--Creazione della tabella avente il numero documento con data e ora e il nome cliente-->
                    <fo:table table-layout="fixed" border-width="none" width="100%" margin-top="2%">
                        <fo:table-column column-width="50%"/>
                        <fo:table-column column-width="50%"/>

                        <fo:table-header>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block font-family="Arial, Helvetica, sans-serif" font-size="16pt" font-weight="bold" text-align="left">
                                        <xsl:value-of select="Scontrino/Didascalia/DidascaliaTitolo/colonnaDT"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block font-family="Arial, Helvetica, sans-serif" font-size="16pt" font-weight="bold" text-align="right">
                                        <xsl:value-of select="Scontrino/Didascalia/DidascaliaTitolo/colonnaNT"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-header>

                        <fo:table-body>
                            <fo:table-row height="3mm"> <!-- Riga vuota per creare spazio -->
                                <fo:table-cell>
                                    <fo:block/>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block font-family="Arial, Helvetica, sans-serif" font-size="16pt" font-weight="normal" text-align="left">
                                        <xsl:value-of select="Scontrino/Didascalia/DidascaliaCorpo/colonnaDC"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block font-family="Arial, Helvetica, sans-serif" font-size="16pt" font-weight="normal" text-align="right">
                                        <xsl:value-of select="Scontrino/Didascalia/DidascaliaCorpo/colonnaNC"/>
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