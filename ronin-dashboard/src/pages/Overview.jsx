import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend } from 'chart.js';
import { Bar } from 'react-chartjs-2';
import { useApp } from "../context/AppContext";

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

export default function Overview() {
  // Get the projects from the global state
  const { projects } = useApp();

  // Calculate dynamic stats
  const totalProjects = projects.length;
  const tasksDue = projects.filter((p) => p.status !== "Completed").length;

  const chartData = {
    labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
    datasets: [
      {
        label: 'Earnings (R)',
        data: [1200, 1900, 800, 2500, 2200, 3000],
        backgroundColor: 'rgba(59, 130, 246, 0.8)',
        borderRadius: 6,
      },
    ],
  };

  const chartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: { position: 'top' },
      title: { display: true, text: 'Monthly Earnings Breakdown', font: { size: 16 } },
    },
    scales: { y: { beginAtZero: true } }
  };

  return (
    <div className="space-y-6">
      {/* Summary Cards */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-100">
          <h3 className="text-gray-500 text-sm font-medium">Total Projects</h3>
          {/* Dynamic Value */}
          <p className="text-3xl font-bold text-gray-800 mt-2">{totalProjects}</p>
        </div>
        <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-100">
          <h3 className="text-gray-500 text-sm font-medium">Total Earnings</h3>
          <p className="text-3xl font-bold text-gray-800 mt-2">R14,500</p>
        </div>
        <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-100">
          <h3 className="text-gray-500 text-sm font-medium">Tasks Due</h3>
          {/* Dynamic Value - Changes color if tasks are due */}
          <p className={`text-3xl font-bold mt-2 ${tasksDue > 0 ? 'text-red-500' : 'text-green-500'}`}>
            {tasksDue}
          </p>
        </div>
      </div>

      {/* Chart Section */}
      <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-100 h-96">
        <Bar options={chartOptions} data={chartData} />
      </div>
    </div>
  );
}