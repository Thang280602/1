<template>
  <VRow>
    <section class="content-header">
      <div class="box box-primary">
        <div class="box-header with-border">
          <h3 class="box-title">Area Chart</h3>
          <div class="box-tools pull-right">
            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
            </button>
            <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
          </div>
        </div>
        <div class="box-body">
          <div class="chart">
            <canvas id="areaChart" style="height: 800px; width: 100%;"></canvas>
          </div>
        </div>
      </div>
    </section>
  </VRow>
</template>

<script>
import { CategoryScale, Chart, Legend, LineController, LineElement, LinearScale, PointElement, Title } from 'chart.js';
import $ from 'jquery';


Chart.register(LineController, LineElement, PointElement, LinearScale, Title, CategoryScale, Legend);

export default {
  name: 'Dashboard',
  mounted() {
    this.fetchChartData();
  },
  methods: {
    fetchChartData() {
      $.ajax({
        url: 'http://localhost:8080/admin/chart',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
          const ctx = document.getElementById('areaChart').getContext('2d');
          const areaChartData = {
            labels: data.labels,
            datasets: [{
              label: 'Đơn hàng',
              backgroundColor: 'rgba(210, 214, 222, 1)',
              borderColor: 'rgba(210, 214, 222, 1)',
              borderWidth: 1,
              yAxisID: 'y-axis-1',
              data: data.totalOrdersData,
            },
            {
              label: 'Tổng giá',
              backgroundColor: 'rgba(60,141,188,0.5)',
              borderColor: 'rgba(60,141,188,0.8)',
              borderWidth: 1,
              yAxisID: 'y-axis-2',
              data: data.totalPriceData,
            }]
          };

          const areaChartOptions = {
            maintainAspectRatio: false,
            responsive: true,
            plugins: {
              legend: {
                display: true,
                labels: {
                  color: 'rgb(255, 99, 132)' // Màu sắc của các nhãn trong legend
                }
              },
              title: {
                display: true,
                text: 'Biểu đồ đơn hàng và tổng giá'
              }
            },
            scales: {
              x: {
                grid: {
                  display: false
                }
              },
              'y-axis-1': {
                type: 'linear',
                position: 'left',
                beginAtZero: true,
                ticks: {
                  stepSize: 1
                }
              },
              'y-axis-2': {
                type: 'linear',
                position: 'right',
                grid: {
                  drawOnChartArea: false
                },
                ticks: {
                  callback: function (value) {
                    return value + ' VND';
                  }
                }
              }
            }
          };

          new Chart(ctx, {
            type: 'line',
            data: areaChartData,
            options: areaChartOptions
          });
        },
        error: function (xhr, status, error) {
          console.error('Error fetching chart data:', error);
        }
      });
    }
  }
};
</script>

<style></style>
