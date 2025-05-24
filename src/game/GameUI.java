package game;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import character.Character;
import character.Player;
import item.Enhancer;
import item.Equipment;

public class GameUI extends JPanel {
    // 현재는 빈 클래스지만 추후 GUI 구성 요소들이 여기에 추가될 예정임
    // 예: 플레이어 정보, 몬스터 정보, 전투 로그, 인벤토리, 버튼 패널 등
    // GameUI 생성자 내부에 컴포넌트 초기화 및 레이아웃 배치가 들어갈 예정

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JPanel pStat = new JPanel();// 스탯
    JPanel pStatView = new JPanel();
    JPanel mStat = new JPanel();// 몹
    JPanel bLog = new JPanel();// 배틀로그
    JPanel eLog = new JPanel();// 장비로그

    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();
    JPanel p2_1 = new JPanel();
    JPanel p3 = new JPanel();

    JScrollPane bLogPane = new JScrollPane();
    JScrollPane invenPane = new JScrollPane();
    JScrollPane eLogPane = new JScrollPane();

    JLabel pStatLabel = new JLabel("");
    JLabel mStatLabel = new JLabel("");
    JLabel gold = new JLabel("Need Enhance Gold: $0");
    JLabel rate = new JLabel("Success Rate: 0%");

    DefaultListModel<String> model = new DefaultListModel<>();

    JList<String> inven = new JList<>(model) {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        @Override
        public String getToolTipText(MouseEvent e) {
            int index = locationToIndex(e.getPoint());
            if (index > -1) {
                Equipment eq = player.getInventory().getItemList().get(index);

                return String.format(
                        "<html>" +
                                "<b>%s</b><br>" +
                                "HP: %.1f | DEF: %.1f<br>" +
                                "DMG: %.1f ~ %.1f<br>" +
                                "BAL: %.1f%% | CRIT: %.1f%% x%.1f<br>" +
                                "<b>CP: %.1f</b>" +
                                "</html>",
                        eq.getName(), eq.getStatus().hp, eq.getStatus().defense,
                        eq.getStatus().damageMin, eq.getStatus().damageMax,
                        eq.getStatus().balance, eq.getStatus().critChance, eq.getStatus().critMul,
                        eq.getStatus().cp
                );
            }
            return null;
        }
    };// 인벤

    JButton enBtn = new JButton("Enhance Selected");
    JButton eBtn = new JButton("Equip Selected");
    JButton sBtn = new JButton("Sell Selected");
    JButton sortBtn = new JButton("Sort by CP");

    Player player;
    List<Equipment> pInven;

    public GameUI(Player player) {
        ToolTipManager.sharedInstance().registerComponent(inven);

        this.player = player;
        pInven = player.getInventory().getItemList();

        setLayout(new BoxLayout(this, 1));

        p1.setLayout(new BoxLayout(p1, 0));
        p2.setLayout(new BoxLayout(p2, 0));
        p2_1.setLayout(new FlowLayout(FlowLayout.LEFT));
        p3.setLayout(new GridLayout(3, 2));

        p2_1.setPreferredSize(new Dimension(510, 500));

        pStat.setLayout(new GridLayout(2, 5));
        pStat.setAlignmentX(Component.LEFT_ALIGNMENT);

        pStatLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        pStatLabel.setBorder(new EmptyBorder(0, -1, 8, 0));

        pStatView.setLayout(new BoxLayout(pStatView, 1));
        pStatView.setBorder(BorderFactory.createTitledBorder("PLAYER"));
        pStatView.add(pStat);
        pStatView.add(pStatLabel);

        mStat.setLayout(new GridLayout(3, 1));
        mStat.setBorder(BorderFactory.createTitledBorder("MONSTER"));
        mStat.setPreferredSize(new Dimension(200, 100));
        mStat.add(mStatLabel);

        bLog.setLayout(new BoxLayout(bLog, 1));

        bLogPane.setPreferredSize(new Dimension(500, 500));
        bLogPane.setViewportView(bLog);

        invenPane.setViewportView(inven);
        invenPane.setBorder(BorderFactory.createTitledBorder("INVENTORY"));
        invenPane.setPreferredSize(new Dimension(500, 200));

        inven.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        inven.setBackground(new Color(238, 238, 238));

        inven.addListSelectionListener(e -> {
            // 마우스로 드래그할 때 중복 호출 방지
            if (!e.getValueIsAdjusting()) {
                int index = inven.getSelectedIndex();
                if (index != -1) {
                    this.setGold(Enhancer.calculateCost(pInven.get(index)));
                    this.setRate(Enhancer.calculateSuccessRate(pInven.get(index)));
                    if(player.getInventory().getItemList().get(inven.getSelectedIndex()).isEquipped()) {
                        eBtn.setText("Unequip Selected");
                    }else {
                        eBtn.setText("Equip Selected");
                    }
                }
            }
        });

        eLog.setLayout(new BoxLayout(eLog, 1));
        eLog.setBorder(BorderFactory.createTitledBorder(""));

        eLogPane.setPreferredSize(new Dimension(500, 100));
        eLogPane.setViewportView(eLog);

        enBtn.setPreferredSize(new Dimension(245, 20));
        eBtn.setPreferredSize(new Dimension(245, 20));
        sBtn.setPreferredSize(new Dimension(245, 20));
        sortBtn.setPreferredSize(new Dimension(245, 20));

        p1.add(pStatView);
        p1.add(mStat);

        enBtn.addActionListener(e -> {// 강화 버튼
            if (inven.getSelectedIndex() != -1) {
                Enhancer.tryEnhance(pInven.get(inven.getSelectedIndex()), player.getInventory(), player);
            }
        });

        eBtn.addActionListener(e -> {// 장착 버튼
            if (!pInven.isEmpty() && inven.getSelectedIndex() != -1) {
                if(player.getInventory().getItemList().get(inven.getSelectedIndex()).isEquipped()) {
                    player.unequipItem(pInven.get(inven.getSelectedIndex()));
                }else {
                    player.equipItem(pInven.get(inven.getSelectedIndex()));
                }

                this.refreshItem();
            }
        });

        sBtn.addActionListener(e -> {// 판매 버튼
            if (!pInven.isEmpty() && inven.getSelectedIndex() != -1) {
                player.getInventory().sellItem(pInven.get(inven.getSelectedIndex()), player);
                //inven.clearSelection();
                this.refreshItem();
            }
        });

        sortBtn.addActionListener(e -> {// 정렬 버튼
            player.getInventory().getItemList().sort(Comparator.comparingDouble(equipment -> -equipment.getStatus().cp));
            inven.clearSelection();
            this.refreshItem();
        });

        p3.add(gold);
        p3.add(rate);
        p3.add(enBtn);
        p3.add(eBtn);
        p3.add(sBtn);
        p3.add(sortBtn);

        p2_1.add(invenPane);
        p2_1.add(eLogPane);
        p2_1.add(p3);

        p2.add(bLogPane);
        p2.add(p2_1);

        this.add(p1);
        this.add(p2);
    }

    /**
     * 배틀 로그 출력
     *
     * @param text 출력할 문자열
     */
    public void bLogPrinter(String text) {
        if (bLog.getComponentCount() == 500) {
            bLog.remove(0);
        }
        bLog.add(new JLabel(text));
        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = bLogPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
        this.revalidate();
        this.repaint();
    }

    /**
     * 플레이어 스탯 갱신
     *
     * @param player Player 객체
     * @param cHp    현재 hp
     */
    void setPlayerStat(Player player, double cHp) {
        pStat.removeAll();

        pStat.add(new JLabel("Name: " + player.getName()));
        pStat.add(new JLabel("Level: " + player.getLevel()));
        pStat.add(new JLabel(
                "HP: " + Math.max(0.0, Math.round(cHp * 10.0) / 10.0) + " / " + Math.round(player.getStatus().hp * 10.0) / 10.0));
        pStat.add(new JLabel(
                "ATK: " + Math.round(player.getStatus().damageMin * 10.0) / 10.0 + "~" + Math.round(player.getStatus().damageMax * 10.0) / 10.0));
        pStat.add(new JLabel("CP: " + Math.round(player.getStatus().cp * 10.0) / 10.0));

        pStat.add(new JLabel("DEF: " + Math.round(player.getStatus().defense * 10.0) / 10.0));
        pStat.add(new JLabel("EXP: " + player.getExpNow() + "/" + player.getExpMax()));
        pStat.add(new JLabel("Gold: $" + player.getMoney()));
        pStat.add(new JLabel("Crit: " + Math.round(player.getStatus().critChance * 10.0) / 10.0 + "% / x"
                + Math.round(player.getStatus().critMul * 10.0) / 10.0 + "%"));
        pStat.add(new JLabel("Balance: " + Math.round(player.getStatus().balance * 10.0) / 10.0 + "%"));

        if (player.getEquipment() != null) {
            pStatLabel.setText(" Equipment: " + player.getEquipment().getName());
        } else {
            pStatLabel.setText(" Equipment: ");
        }
    }

    void setMonsterStat(Character monster, double cHp) {
        mStat.removeAll();

        mStat.add(new JLabel("Name: " + monster.getName()));
        mStat.add(new JLabel(
                "HP: " + Math.max(0.0, Math.round(cHp * 10.0) / 10.0) + " / " + Math.round(monster.getStatus().hp * 10.0) / 10.0));
        mStat.add(new JLabel("CP: " + Math.round(monster.getStatus().cp * 10.0) / 10.0));
    }


    void addItem(String name) {
        model.addElement(name);
    }

    /**
     * 보이는 장비 목록 갱신
     */
    void refreshItem() {
        int selectedIndex = inven.getSelectedIndex(); // 포커스 저장
        model.clear();
        for (Equipment equipment : pInven) {
            this.addItem(equipment.getName());
        }
        if (selectedIndex != -1 && selectedIndex < model.getSize()) {
            inven.setSelectedIndex(selectedIndex);
            inven.ensureIndexIsVisible(selectedIndex);
        }
    }

    /**
     * 장비 로그 출력
     *
     * @param text 출력할 문자열
     */
    public void eLogPrinter(String text) {
        if (eLog.getComponentCount() == 100) {
            eLog.remove(0);
        }
        eLog.add(new JLabel(text));
        this.revalidate();
        this.repaint();

        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = eLogPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
    }

    /**
     * 골드량 갱신
     *
     * @param g 골드량
     */
    void setGold(int g) {
        gold.setText("Need Enhance Gold: $" + g);
    }

    /**
     * 성공 확률 갱신
     *
     * @param r 성공 확률
     */
    void setRate(double r) {
        rate.setText("Success Rate: " + Math.round(r * 100.0) + "%");
    }

    public void refreshAll(double pStatCHp, Character monster, double mStatCHp) {
        this.refreshItem();
        this.setPlayerStat(player, pStatCHp);
        this.setMonsterStat(monster, mStatCHp);
        this.revalidate();
        this.repaint();
    }
}