package edu.re.estate.presenters.chatbot;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import edu.re.estate.data.models.ChatBotMsg;
import edu.re.estate.databinding.ViewChatBoxChatBinding;

public class ChatBot2Adapter extends RecyclerView.Adapter<ChatBot2Adapter.ChatBotViewHolder> {
    private List<ChatBotMsg> chatHistory = new ArrayList<>();

    public void setChatBotMsgs(List<ChatBotMsg> list) {
        this.chatHistory = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatBotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // DataBinding sẽ tự tạo Class này từ file view_chat_box_chat.xml
        ViewChatBoxChatBinding binding = ViewChatBoxChatBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ChatBotViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatBotViewHolder holder, int position) {
        ChatBotMsg msg = chatHistory.get(position);

        if (msg.isSelf()) {
            // Nếu là tin nhắn người dùng: Hiện layout phải, ẩn layout trái
            holder.binding.layoutRight.setVisibility(View.VISIBLE);
            holder.binding.layoutLeft.setVisibility(View.GONE);
            holder.binding.tvQuestion.setText(msg.getContent());
        } else {
            // Nếu là tin nhắn AI: Hiện layout trái, ẩn layout phải
            holder.binding.layoutLeft.setVisibility(View.VISIBLE);
            holder.binding.layoutRight.setVisibility(View.GONE);
            holder.binding.tvAnswer.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() { return chatHistory.size(); }

    static class ChatBotViewHolder extends RecyclerView.ViewHolder {
        ViewChatBoxChatBinding binding;
        ChatBotViewHolder(ViewChatBoxChatBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}