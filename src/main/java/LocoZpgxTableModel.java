import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * 机车支配关系表格模型。
 * 说明：保留传统 Swing TableModel 直观写法，便于老系统改造。
 */
public class LocoZpgxTableModel extends AbstractTableModel {

    private final String[] columns = {
            "序号", "机型(model)", "机车号码(loco)", "支配关系(ZPGX)", "开始时间(changetime)", "结束时间(endtime)", "创建的机务段(createpostid)"
    };

    private List<LocoZpgxVO> data = new ArrayList<LocoZpgxVO>();

    public void setData(List<LocoZpgxVO> data) {
        this.data = data;
        fireTableDataChanged();
    }

    public List<LocoZpgxVO> getData() {
        return data;
    }

    public LocoZpgxVO getRow(int row) {
        if (row < 0 || row >= data.size()) {
            return null;
        }
        return data.get(row);
    }

    public void addRow(LocoZpgxVO vo) {
        data.add(vo);
        int index = data.size() - 1;
        fireTableRowsInserted(index, index);
    }

    public LocoZpgxVO removeRow(int row) {
        if (row < 0 || row >= data.size()) {
            return null;
        }
        LocoZpgxVO removed = data.remove(row);
        fireTableRowsDeleted(row, row);
        return removed;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        LocoZpgxVO vo = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return vo.getModel();
            case 2:
                return vo.getLoco();
            case 3:
                return vo.getZPGX();
            case 4:
                return vo.getChangetime();
            case 5:
                return vo.getEndtime();
            case 6:
                return vo.getCreatepostid();
            default:
                return "";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // 序号不可编辑，其余列允许直接修改
        return columnIndex != 0;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        LocoZpgxVO vo = data.get(rowIndex);
        String value = aValue == null ? "" : aValue.toString().trim();

        switch (columnIndex) {
            case 1:
                vo.setModel(value);
                break;
            case 2:
                vo.setLoco(value);
                break;
            case 3:
                vo.setZPGX(value);
                break;
            case 4:
                vo.setChangetime(value);
                break;
            case 5:
                vo.setEndtime(value);
                break;
            case 6:
                vo.setCreatepostid(value);
                break;
            default:
                break;
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }
}
