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

                    <!--Creazione della tabella-->
                    <fo:table border="none" width="100%">
                        <fo:table-column column-width="50%"/>
                        <fo:table-column column-width="25%"/>
                        <fo:table-column column-width="25%"/>

                    </fo:table>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>