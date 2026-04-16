import React from 'react';
import { Text, View } from 'react-native';

export default function DisclaimerBanner() {
  return (
    <View style={{ backgroundColor: '#fff7e6', borderRadius: 8, padding: 10, marginVertical: 8 }}>
      <Text style={{ fontWeight: '600' }}>Informational only. Not medical advice.</Text>
      <Text>Please consult a qualified doctor.</Text>
    </View>
  );
}
