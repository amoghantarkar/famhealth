import React from 'react';
import { ScrollView, Text, View } from 'react-native';
import DisclaimerBanner from '../components/DisclaimerBanner';

export default function HomeScreen() {
  return (
    <ScrollView style={{ flex: 1, padding: 16 }}>
      <Text style={{ fontSize: 24, fontWeight: '700' }}>Organize your health records in one place</Text>
      <Text style={{ marginTop: 8 }}>Track trends over time and keep records for self, parent, spouse, and child profiles.</Text>
      <DisclaimerBanner />
      <View style={{ marginTop: 12 }}>
        <Text>• AI-generated summaries may need review.</Text>
        <Text>• Verify extracted values before relying on them.</Text>
      </View>
    </ScrollView>
  );
}
