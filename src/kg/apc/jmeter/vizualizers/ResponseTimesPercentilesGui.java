package kg.apc.jmeter.vizualizers;

import java.awt.Color;
import kg.apc.jmeter.JMeterPluginsUtils;
import kg.apc.jmeter.charting.AbstractGraphRow;
import kg.apc.jmeter.charting.GraphRowPercentiles;
import org.apache.jmeter.samplers.SampleResult;

/**
 *
 * @author Stephane Hoblingre
 */
public class ResponseTimesPercentilesGui
        extends AbstractGraphPanelVisualizer
{

    /**
     *
     */
    public ResponseTimesPercentilesGui()
    {
        super();
        graphPanel.getGraphObject().setForcedMinX(0);
        graphPanel.getGraphObject().setxAxisLabel("Percentiles");
        graphPanel.getGraphObject().setyAxisLabel("Percentile value in ms");
    }

    @Override
    public String getLabelResource()
    {
        return this.getClass().getSimpleName();
    }

    @Override
    public String getStaticLabel()
    {
        return JMeterPluginsUtils.prefixLabel("Response Times Percentiles");
    }

    @Override
    public void add(SampleResult res)
    {
        String label = res.getSampleLabel();
        String aggregateLabel = "Overall Response Times";
        GraphRowPercentiles row = (GraphRowPercentiles) model.get(label);
        GraphRowPercentiles rowAgg = (GraphRowPercentiles) modelAggregate.get(label);

        if (row == null)
        {
            row = (GraphRowPercentiles) getNewRow(model, AbstractGraphRow.ROW_PERCENTILES, label, AbstractGraphRow.MARKER_SIZE_SMALL, false, false, false, true, false);
        }
        if (rowAgg == null)
        {
            rowAgg = (GraphRowPercentiles) getNewRow(modelAggregate, AbstractGraphRow.ROW_PERCENTILES, aggregateLabel, AbstractGraphRow.MARKER_SIZE_SMALL, false, false, false, true, Color.RED, false);
        }

        row.addResponseTime(res.getTime());
        rowAgg.addResponseTime(res.getTime());
        updateGui(null);
    }

    @Override
    protected JSettingsPanel getSettingsPanel()
    {
        return new JSettingsPanel(this,
                JSettingsPanel.GRADIENT_OPTION |
                JSettingsPanel.MAXY_OPTION |
                JSettingsPanel.AGGREGATE_OPTION);
    }

    @Override
    public String getWikiPage() {
        return "RespTimePercentiles";
    }
}
