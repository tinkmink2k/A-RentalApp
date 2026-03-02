package edu.re.estate.data.models;

public class ChatBotMsg {
    private int id;
    private int categoryId;
    private String content; // Câu hỏi
    private String answer;  // Câu trả lời
    private boolean isSelf; // true: Người dùng, false: Bot

    public ChatBotMsg() {}


    public ChatBotMsg(int id, int categoryId, String content, String answer) {
        this.id = id;
        this.categoryId = categoryId;
        this.content = content;
        this.answer = answer;
        this.isSelf = false;
    }

    public ChatBotMsg(String content, boolean isSelf) {
        this.content = content;
        this.isSelf = isSelf;
    }

    // Getter và Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
    public boolean isSelf() { return isSelf; }
    public void setSelf(boolean self) { isSelf = self; }
}