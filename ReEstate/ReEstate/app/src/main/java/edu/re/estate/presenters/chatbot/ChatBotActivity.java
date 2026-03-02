package edu.re.estate.presenters.chatbot;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import java.util.List;
import edu.re.estate.R;
import edu.re.estate.data.models.ChatBotMsg;
import edu.re.estate.databinding.ActivityChatBotBinding;

public class ChatBotActivity extends AppCompatActivity {
    private ActivityChatBotBinding binding;
    private final List<ChatBotMsg> masterList = new ArrayList<>(); // Dữ liệu gốc
    private final List<ChatBotMsg> historyList = new ArrayList<>(); // Lịch sử chat
    private ChatBot2Adapter historyAdapter;
    private ChatBotAdapter suggestionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBotBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initData();
        setupRecyclers();
        setupChips();

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

        // Tin nhắn chào mừng
        addBotMessage("Chào bạn! Tôi là trợ lý Re-Estate. Hãy chọn chủ đề bạn quan tâm bên dưới nhé!");
    }

    private void setupRecyclers() {
        // Lịch sử chat (Dọc)
        historyAdapter = new ChatBot2Adapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(historyAdapter);

        // Gợi ý câu hỏi (Ngang)
        suggestionAdapter = new ChatBotAdapter(new ArrayList<>(), msg -> handleSelection(msg));
        binding.recycler2.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler2.setAdapter(suggestionAdapter);

        filterByCategory(1); // Mặc định hiện nhóm Giá cả
    }

    private void handleSelection(ChatBotMsg msg) {
        // 1. User hỏi
        historyList.add(new ChatBotMsg(msg.getContent(), true));
        historyAdapter.setChatBotMsgs(new ArrayList<>(historyList));
        binding.recyclerView.smoothScrollToPosition(historyList.size() - 1);

        // 2. Bot trả lời sau 800ms
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            historyList.add(new ChatBotMsg(msg.getAnswer(), false));
            historyAdapter.setChatBotMsgs(new ArrayList<>(historyList));
            binding.recyclerView.smoothScrollToPosition(historyList.size() - 1);
        }, 800);
    }

    private void setupChips() {
        binding.chipGroupCategories.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) return;
            int id = checkedIds.get(0);
            if (id == R.id.chipPrice) filterByCategory(1);
            else if (id == R.id.chipProcess) filterByCategory(2);
            else if (id == R.id.chipSupport) filterByCategory(3);
            else if (id == R.id.chipLegal) filterByCategory(4);
            else if (id == R.id.chipInvest) filterByCategory(5);
            else if (id == R.id.chipArea) filterByCategory(6);
            else if (id == R.id.chipAccount) filterByCategory(7);

        });
    }

    private void filterByCategory(int catId) {
        List<ChatBotMsg> filtered = new ArrayList<>();
        for (ChatBotMsg m : masterList) {
            if (m.getCategoryId() == catId) filtered.add(m);
        }
        suggestionAdapter.updateData(filtered);

        binding.recycler2.scrollToPosition(0);
    }

    private void addBotMessage(String text) {
        historyList.add(new ChatBotMsg(text, false));
        historyAdapter.setChatBotMsgs(new ArrayList<>(historyList));
    }

    private void initData() {
        // --- CHỦ ĐỀ 1: GIÁ CẢ VÀ CHI PHÍ (cc_1 -> ID: 1) ---
        masterList.add(new ChatBotMsg(1, 1, "Giá cả dịch vụ của công ty như thế nào?",
                "Giá cả dịch vụ của chúng tôi được thiết kế để phù hợp với nhiều đối tượng khách hàng khác nhau. Vui lòng liên hệ với bộ phận chăm sóc khách hàng để nhận báo giá chi tiết."));
        masterList.add(new ChatBotMsg(2, 1, "Có các phương thức thanh toán nào được chấp nhận?",
                "Chúng tôi chấp nhận nhiều phương thức thanh toán bao gồm chuyển khoản ngân hàng, thẻ tín dụng, và thanh toán trực tiếp tại văn phòng."));
        masterList.add(new ChatBotMsg(3, 1, "Các loại thuế phí nào phát sinh khi sang tên sổ đỏ?",
                "Thuế thu nhập cá nhân (2%), Lệ phí trước bạ (0.5%), và các phí thẩm định hồ sơ, phí công chứng."));
        masterList.add(new ChatBotMsg(4, 1, "Công ty có hỗ trợ vay vốn ngân hàng không?",
                "Chúng tôi có hợp tác với nhiều ngân hàng để hỗ trợ khách hàng vay vốn mua bất động sản với lãi suất ưu đãi."));
        masterList.add(new ChatBotMsg(5, 1, "Giá thuê nhà thường đã bao gồm phí quản lý chưa?",
                "Tùy vào từng hợp đồng. Thông thường chưa bao gồm. Vui lòng kiểm tra kỹ hợp đồng thuê nhà để biết chi tiết."));

        // --- CHỦ ĐỀ 2: QUY TRÌNH MUA/BÁN/THUÊ (cc_2 -> ID: 2) ---
        masterList.add(new ChatBotMsg(6, 2, "Quy trình mua nhà tại công ty như thế nào?",
                "Bao gồm: Tư vấn, Thẩm định pháp lý, Ký hợp đồng đặt cọc, Ký hợp đồng mua bán, và Hoàn tất thủ tục sang tên."));
        masterList.add(new ChatBotMsg(7, 2, "Tôi cần chuẩn bị những giấy tờ gì để bán nhà?",
                "Bạn cần: Giấy chứng nhận quyền sử dụng đất (Sổ hồng/đỏ), CMND/CCCD, Hộ khẩu và giấy xác nhận tình trạng hôn nhân."));
        masterList.add(new ChatBotMsg(8, 2, "Sau khi ký công chứng, bao lâu tôi nhận được sổ hồng mới?",
                "Thời gian thường từ 15 đến 30 ngày làm việc, tùy thuộc vào quy trình của cơ quan nhà nước địa phương."));
        masterList.add(new ChatBotMsg(10,2, "Quy trình thuê nhà tại công ty như thế nào?",
                "Quy trình thuê nhà bao gồm các bước: Tư vấn và lựa chọn bất động sản, Thẩm định pháp lý, Ký hợp đồng thuê nhà, và Thanh toán tiền thuê. Vui lòng liên hệ để được hướng dẫn chi tiết."));
        masterList.add(new ChatBotMsg(11,2, "Tôi có thể hủy hợp đồng mua bán nhà không?",
                "Việc hủy hợp đồng mua bán nhà phụ thuộc vào các điều khoản đã thỏa thuận trong hợp đồng. Vui lòng liên hệ với chúng tôi để được tư vấn cụ thể về trường hợp của bạn."));

        // ---- CHỦ ĐỀ 3: DỊCH VỤ HỖ TRỢ KHÁCH HÀNG (cc_3 -> ID: 3) ---


        // --- CHỦ ĐỀ 4: PHÁP LÝ VÀ GIẤY TỜ (cc_4 -> ID: 4) ---
        masterList.add(new ChatBotMsg(12, 4, "Những giấy tờ cần thiết để mua nhà là gì?",
                "Bao gồm: CMND/CCCD, Hộ khẩu, Giấy xác nhận tình trạng hôn nhân và hồ sơ chứng minh tài chính nếu có vay vốn."));
        masterList.add(new ChatBotMsg(13, 4, "Làm sao để kiểm tra tính pháp lý của một bất động sản?",
                "Bạn có thể kiểm tra tại văn phòng đăng ký đất đai hoặc nhờ đội ngũ chuyên gia của chúng tôi thẩm định trực tiếp."));
        masterList.add(new ChatBotMsg(14, 4, "Thủ tục sang tên sổ đỏ bao gồm những bước nào?",
                "Gồm: Ký hợp đồng công chứng, Nộp hồ sơ tại VP Đăng ký đất đai, Đóng thuế phí và chờ nhận kết quả."));

        // --- CHỦ ĐỀ 5: PHÁP LÝ VÀ GIẤY TỜ (cc_4 -> ID: 4) ---


        // --- CHỦ ĐỀ 6: PHÁP LÝ VÀ GIẤY TỜ (cc_4 -> ID: 4) ---


        // --- CHỦ ĐỀ 7: ĐĂNG TIN VÀ TÀI KHOẢN (cc_7 -> ID: 7) ---
        masterList.add(new ChatBotMsg(15, 7, "Làm sao để đăng tin bán nhà trên website?",
                "Đăng nhập, chọn mục 'Đăng tin', điền thông tin mô tả, giá và tải ảnh thực tế của bất động sản lên."));
        masterList.add(new ChatBotMsg(16, 7, "Tôi quên mật khẩu tài khoản, làm sao để lấy lại?",
                "Sử dụng chức năng 'Quên mật khẩu' tại màn hình đăng nhập, hệ thống sẽ gửi mã reset qua Email/SĐT của bạn."));
    }
}