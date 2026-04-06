import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

/**
 * 机车支配关系维护主页面。
 * 启动入口：main 方法。
 */
public class LocoZpgxManageFrame extends JFrame {

    // 查询条件输入框
    private JTextField modelQueryField;
    private JTextField locoQueryField;
    private JTextField zpgxQueryField;
    private JTextField createPostQueryField;

    // 表格及模型
    private JTable table;
    private LocoZpgxTableModel tableModel;

    // 内存数据（模拟数据库）
    private List<LocoZpgxVO> allData = new ArrayList<LocoZpgxVO>();

    public LocoZpgxManageFrame() {
        initLookAndFeel();
        initData();
        initFrame();
        initUI();
        bindEvents();
    }

    /**
     * 使用系统默认外观，保持传统客户端风格。
     */
    private void initLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // 忽略，使用 Swing 默认外观
        }
    }

    /**
     * 初始化内存假数据。
     */
    private void initData() {
        allData.add(new LocoZpgxVO("7001", "7001", "武汉", "19:00", "06:00", "武东"));
        allData.add(new LocoZpgxVO("7001", "7001", "株洲", "06:00", "13:00", "株段"));
        allData.add(new LocoZpgxVO("7001", "7001", "株洲", "13:00", "18:00", "临修"));
        allData.add(new LocoZpgxVO("HXD1", "0156", "武汉", "08:00", "16:00", "汉口"));
        allData.add(new LocoZpgxVO("SS9", "0042", "襄阳", "16:00", "23:00", "襄段"));
    }

    /**
     * 初始化窗口属性。
     */
    private void initFrame() {
        setTitle("机车支配关系维护");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(1080, 620));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(4, 4));
    }

    /**
     * 初始化界面组件。
     */
    private void initUI() {
        add(createNorthPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
    }

    /**
     * 创建上方查询与按钮区域。
     */
    private JPanel createNorthPanel() {
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBorder(BorderFactory.createTitledBorder("查询条件"));

        JPanel queryPanel = new JPanel(new GridBagLayout());
        queryPanel.setBackground(new Color(240, 240, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel modelLabel = createQueryLabel("机型:");
        JLabel locoLabel = createQueryLabel("机车号码:");
        JLabel zpgxLabel = createQueryLabel("支配关系:");
        JLabel createPostLabel = createQueryLabel("创建机务段:");

        modelQueryField = new JTextField(10);
        locoQueryField = new JTextField(10);
        zpgxQueryField = new JTextField(10);
        createPostQueryField = new JTextField(10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        queryPanel.add(modelLabel, gbc);
        gbc.gridx = 1;
        queryPanel.add(modelQueryField, gbc);

        gbc.gridx = 2;
        queryPanel.add(locoLabel, gbc);
        gbc.gridx = 3;
        queryPanel.add(locoQueryField, gbc);

        gbc.gridx = 4;
        queryPanel.add(zpgxLabel, gbc);
        gbc.gridx = 5;
        queryPanel.add(zpgxQueryField, gbc);

        gbc.gridx = 6;
        queryPanel.add(createPostLabel, gbc);
        gbc.gridx = 7;
        queryPanel.add(createPostQueryField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 6, 2, 0));

        JButton queryBtn = new JButton("查询");
        queryBtn.setActionCommand("QUERY");
        JButton resetBtn = new JButton("重置");
        resetBtn.setActionCommand("RESET");
        JButton addRowBtn = new JButton("新增一行");
        addRowBtn.setActionCommand("ADD_ROW");
        JButton deleteRowBtn = new JButton("删除选中行");
        deleteRowBtn.setActionCommand("DELETE_ROW");
        JButton saveBtn = new JButton("保存");
        saveBtn.setActionCommand("SAVE");

        buttonPanel.add(queryBtn);
        buttonPanel.add(resetBtn);
        buttonPanel.add(addRowBtn);
        buttonPanel.add(deleteRowBtn);
        buttonPanel.add(saveBtn);

        // 统一事件绑定
        java.awt.event.ActionListener actionListener = e -> {
            String cmd = e.getActionCommand();
            if ("QUERY".equals(cmd)) {
                doQuery();
            } else if ("RESET".equals(cmd)) {
                doReset();
            } else if ("ADD_ROW".equals(cmd)) {
                doAddRow();
            } else if ("DELETE_ROW".equals(cmd)) {
                doDeleteSelectedRow();
            } else if ("SAVE".equals(cmd)) {
                doSave();
            }
        };

        queryBtn.addActionListener(actionListener);
        resetBtn.addActionListener(actionListener);
        addRowBtn.addActionListener(actionListener);
        deleteRowBtn.addActionListener(actionListener);
        saveBtn.addActionListener(actionListener);

        northPanel.add(queryPanel, BorderLayout.NORTH);
        northPanel.add(buttonPanel, BorderLayout.SOUTH);
        return northPanel;
    }

    private JLabel createQueryLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("宋体", Font.PLAIN, 12));
        return label;
    }

    /**
     * 创建表格区域。
     */
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("机车支配关系列表"));

        tableModel = new LocoZpgxTableModel();
        tableModel.setData(new ArrayList<LocoZpgxVO>(allData));

        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // 设置列宽，尽量贴近传统管理系统表格
        table.getColumnModel().getColumn(0).setPreferredWidth(60);
        table.getColumnModel().getColumn(1).setPreferredWidth(140);
        table.getColumnModel().getColumn(2).setPreferredWidth(160);
        table.getColumnModel().getColumn(3).setPreferredWidth(160);
        table.getColumnModel().getColumn(4).setPreferredWidth(170);
        table.getColumnModel().getColumn(5).setPreferredWidth(170);
        table.getColumnModel().getColumn(6).setPreferredWidth(220);

        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    /**
     * 当前页面不需要额外绑定事件，预留给后续扩展。
     */
    private void bindEvents() {
        // 预留
    }

    /**
     * 查询：按输入条件进行模糊匹配。
     */
    private void doQuery() {
        String model = normalize(modelQueryField.getText());
        String loco = normalize(locoQueryField.getText());
        String zpgx = normalize(zpgxQueryField.getText());
        String createPost = normalize(createPostQueryField.getText());

        List<LocoZpgxVO> filtered = new ArrayList<LocoZpgxVO>();
        for (LocoZpgxVO vo : allData) {
            if (!containsIgnoreCase(vo.getModel(), model)) {
                continue;
            }
            if (!containsIgnoreCase(vo.getLoco(), loco)) {
                continue;
            }
            if (!containsIgnoreCase(vo.getZPGX(), zpgx)) {
                continue;
            }
            if (!containsIgnoreCase(vo.getCreatepostid(), createPost)) {
                continue;
            }
            filtered.add(vo);
        }
        tableModel.setData(filtered);
    }

    /**
     * 重置：清空查询条件并恢复全部数据。
     */
    private void doReset() {
        modelQueryField.setText("");
        locoQueryField.setText("");
        zpgxQueryField.setText("");
        createPostQueryField.setText("");
        tableModel.setData(new ArrayList<LocoZpgxVO>(allData));
    }

    /**
     * 新增一行：直接在表格末尾追加空白记录。
     */
    private void doAddRow() {
        LocoZpgxVO newVo = new LocoZpgxVO("", "", "", "", "", "");
        allData.add(newVo);
        tableModel.addRow(newVo);

        int lastRow = tableModel.getRowCount() - 1;
        if (lastRow >= 0) {
            table.setRowSelectionInterval(lastRow, lastRow);
            table.scrollRectToVisible(table.getCellRect(lastRow, 1, true));
            table.editCellAt(lastRow, 1);
        }
    }

    /**
     * 删除选中行。
     */
    private void doDeleteSelectedRow() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "请先选择要删除的行。", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocoZpgxVO removed = tableModel.removeRow(row);
        if (removed != null) {
            allData.remove(removed);
        }
    }

    /**
     * 保存：统一校验后回写内存数据。
     */
    private void doSave() {
        // 如果单元格正处于编辑状态，先结束编辑，确保数据入模
        if (table.isEditing() && table.getCellEditor() != null) {
            table.getCellEditor().stopCellEditing();
        }

        List<LocoZpgxVO> currentData = tableModel.getData();
        for (int i = 0; i < currentData.size(); i++) {
            LocoZpgxVO vo = currentData.get(i);
            int rowNumber = i + 1;

            if (isBlank(vo.getModel())) {
                showValidateError(rowNumber, "机型(model)");
                return;
            }
            if (isBlank(vo.getLoco())) {
                showValidateError(rowNumber, "机车号码(loco)");
                return;
            }
            if (isBlank(vo.getZPGX())) {
                showValidateError(rowNumber, "支配关系(ZPGX)");
                return;
            }
            if (isBlank(vo.getChangetime())) {
                showValidateError(rowNumber, "开始时间(changetime)");
                return;
            }
            if (isBlank(vo.getEndtime())) {
                showValidateError(rowNumber, "结束时间(endtime)");
                return;
            }
            if (isBlank(vo.getCreatepostid())) {
                showValidateError(rowNumber, "创建的机务段(createpostid)");
                return;
            }
        }

        // 回写动作：当前编辑已直接作用于 VO，这里用新列表承载作为“保存后内存状态”
        allData = new ArrayList<LocoZpgxVO>(allData);
        JOptionPane.showMessageDialog(this, "保存成功。", "提示", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showValidateError(int rowNumber, String fieldName) {
        JOptionPane.showMessageDialog(this,
                "第 " + rowNumber + " 行数据有问题：" + fieldName + " 不能为空。",
                "校验失败",
                JOptionPane.WARNING_MESSAGE);
    }

    private String normalize(String s) {
        return s == null ? "" : s.trim();
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private boolean containsIgnoreCase(String source, String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return true;
        }
        if (source == null) {
            return false;
        }
        return source.toLowerCase().contains(keyword.toLowerCase());
    }

    /**
     * 启动入口（可直接在 IDEA 运行）。
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LocoZpgxManageFrame frame = new LocoZpgxManageFrame();
            frame.setVisible(true);
        });
    }
}
