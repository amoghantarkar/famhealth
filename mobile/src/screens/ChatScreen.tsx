import React, { useState } from 'react';
import { Button, ScrollView, Text, TextInput, View } from 'react-native';
import { api } from '../api/client';
import DisclaimerBanner from '../components/DisclaimerBanner';

export default function ChatScreen() {
  const [question, setQuestion] = useState('What are the latest glucose and HbA1c values?');
  const [answer, setAnswer] = useState('');
  const ask = async () => {
    const session = await api.post('/chat/sessions', { profileId: 1, title: 'My Records Chat' });
    const response = await api.post(`/chat/sessions/${session.data.id}/messages`, { content: question });
    setAnswer(response.data.messages?.[0]?.content || 'No answer available.');
  };

  return (
    <ScrollView style={{ flex: 1, padding: 16 }}>
      <Text style={{ fontSize: 22, fontWeight: '700' }}>Chat with your records</Text>
      <TextInput style={{ borderWidth: 1, borderColor: '#ccc', padding: 10, marginTop: 12 }} value={question} onChangeText={setQuestion} />
      <Button title="Ask" onPress={ask} />
      <Text style={{ marginTop: 14 }}>{answer}</Text>
      <DisclaimerBanner />
    </ScrollView>
  );
}
