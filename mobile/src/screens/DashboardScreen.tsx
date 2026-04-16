import React, { useEffect, useState } from 'react';
import { Dimensions, ScrollView, Text, View } from 'react-native';
import { LineChart } from 'react-native-chart-kit';
import { api } from '../api/client';
import DisclaimerBanner from '../components/DisclaimerBanner';

export default function DashboardScreen() {
  const [labels, setLabels] = useState<string[]>(['T1', 'T2']);
  const [values, setValues] = useState<number[]>([95, 110]);
  useEffect(() => {
    api.get('/profiles/1/dashboard').then((r) => {
      const metrics = r.data.metrics || [];
      setLabels(metrics.slice(0, 6).map((m: any) => m.metricName));
      setValues(metrics.slice(0, 6).map((m: any) => Number(m.latestValue || 0)));
    }).catch(() => {});
  }, []);

  return (
    <ScrollView style={{ flex: 1, padding: 16 }}>
      <Text style={{ fontSize: 22, fontWeight: '700' }}>Dashboard & Trends</Text>
      <Text>Glucose, HbA1c, cholesterol, hemoglobin, creatinine, blood pressure, weight.</Text>
      <LineChart
        data={{ labels, datasets: [{ data: values }] }}
        width={Dimensions.get('window').width - 32}
        height={220}
        chartConfig={{ backgroundColor: '#fff', backgroundGradientFrom: '#fff', backgroundGradientTo: '#fff', decimalPlaces: 1, color: () => '#4f46e5' }}
        bezier
      />
      <DisclaimerBanner />
    </ScrollView>
  );
}
