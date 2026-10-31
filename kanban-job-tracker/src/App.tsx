import { useState, useMemo, useEffect } from 'react';
import {
  DndContext, DragOverlay, closestCorners, KeyboardSensor, PointerSensor, useSensor, useSensors, type DragStartEvent, type DragOverEvent, type DragEndEvent,
} from '@dnd-kit/core';
import { arrayMove, sortableKeyboardCoordinates } from '@dnd-kit/sortable';
import { Download, Moon, Sun } from 'lucide-react';
import { useLocalStorage } from './hooks/useLocalStorage';
import type { Job, Status, Priority } from './types';
import { Column } from './components/Column';
import { JobCard } from './components/JobCard';
import { AddJobModal } from './components/AddJobModal';

const COLUMNS: Status[] = ['Applied', 'Interviewing', 'Offer', 'Rejected'];

export default function App() {
  const { storedValue: jobs, setValue: setJobs, isLoading } = useLocalStorage<Job[]>('kanban-jobs', []);
  const [activeJob, setActiveJob] = useState<Job | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');
  const [filterPriority, setFilterPriority] = useState<'All' | Priority>('All');
  
  // NEW: Theme State
  const [isDarkMode, setIsDarkMode] = useState(true);

  // NEW: Apply theme to HTML tag
  useEffect(() => {
    if (isDarkMode) {
      document.documentElement.classList.add('dark');
    } else {
      document.documentElement.classList.remove('dark');
    }
  }, [isDarkMode]);

  const sensors = useSensors(
    useSensor(PointerSensor, { activationConstraint: { distance: 5 } }),
    useSensor(KeyboardSensor, { coordinateGetter: sortableKeyboardCoordinates })
  );

  const filteredJobs = useMemo(() => {
    return jobs.filter((job) => {
      const matchesSearch = job.company.toLowerCase().includes(searchTerm.toLowerCase()) || job.role.toLowerCase().includes(searchTerm.toLowerCase());
      const matchesPriority = filterPriority === 'All' || job.priority === filterPriority;
      return matchesSearch && matchesPriority;
    });
  }, [jobs, searchTerm, filterPriority]);

  const deleteJob = (id: string) => setJobs((prev) => prev.filter(job => job.id !== id));

  const exportToCSV = () => {
    const headers = ['Company', 'Role', 'Status', 'Priority', 'Date Added'];
    const csvRows = jobs.map(job => `"${job.company}","${job.role}","${job.status}","${job.priority}","${job.dateAdded}"`);
    const csvContent = [headers.join(','), ...csvRows].join('\n');
    const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
    const url = URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', 'job_applications.csv');
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  };

  const handleDragStart = (event: DragStartEvent) => {
    const job = jobs.find((j) => j.id === event.active.id);
    if (job) setActiveJob(job);
  };

  const handleDragOver = (event: DragOverEvent) => {
    const { active, over } = event;
    if (!over) return;
    const activeId = active.id;
    const overId = over.id;
    if (activeId === overId) return;
    const isActiveAJob = jobs.some((j) => j.id === activeId);
    const isOverAJob = jobs.some((j) => j.id === overId);
    if (!isActiveAJob) return;

    if (isActiveAJob && isOverAJob) {
      setJobs((prev) => {
        const activeIndex = prev.findIndex((j) => j.id === activeId);
        const overIndex = prev.findIndex((j) => j.id === overId);
        if (prev[activeIndex].status !== prev[overIndex].status) {
          const newJobs = [...prev];
          newJobs[activeIndex].status = prev[overIndex].status;
          return arrayMove(newJobs, activeIndex, overIndex);
        }
        return arrayMove(prev, activeIndex, overIndex);
      });
    }
    if (isActiveAJob && !isOverAJob) {
      setJobs((prev) => {
        const activeIndex = prev.findIndex((j) => j.id === activeId);
        if (prev[activeIndex].status !== overId) {
          const newJobs = [...prev];
          newJobs[activeIndex].status = overId as Status;
          return newJobs;
        }
        return prev;
      });
    }
  };

  const handleDragEnd = (event: DragEndEvent) => {
    setActiveJob(null);
    const { active, over } = event;
    if (!over) return;
    const activeId = active.id;
    const overId = over.id;
    if (activeId === overId) return;
    setJobs((prev) => {
      const activeIndex = prev.findIndex((j) => j.id === activeId);
      const overIndex = prev.findIndex((j) => j.id === overId);
      return arrayMove(prev, activeIndex, overIndex);
    });
  };

  if (isLoading) {
    return (
      <div className="min-h-screen bg-gray-50 dark:bg-[#0a0a0a] p-8 flex gap-6 transition-colors">
        {[1, 2, 3, 4].map((col) => (
          <div key={col} className="w-full md:w-80 bg-gray-200 dark:bg-[#141414] border border-gray-300 dark:border-gray-800 animate-pulse rounded-2xl h-150 p-4 space-y-4">
            <div className="h-6 bg-gray-300 dark:bg-gray-800 rounded w-1/2 mb-6"></div>
            <div className="h-32 bg-gray-300 dark:bg-gray-800 rounded-xl"></div>
          </div>
        ))}
      </div>
    );
  }

  return (
    // Main Wrapper: Changes from light gray to deep black
    <div className="min-h-screen bg-gray-50 dark:bg-[#0a0a0a] text-gray-900 dark:text-white p-4 md:p-8 transition-colors duration-300">
      <div className="max-w-7xl mx-auto">
        
        <header className="mb-10 space-y-6">
          <div className="flex justify-between items-center">
            <div>
              <h1 className="text-3xl font-bold tracking-tight">Job Tracker</h1>
              <p className="text-gray-500 dark:text-gray-500 mt-1">Manage your career pipeline</p>
            </div>
            
            <div className="flex items-center gap-4">
              <button onClick={exportToCSV} className="p-2 text-gray-500 dark:text-gray-400 hover:text-black dark:hover:text-white bg-white dark:bg-[#141414] border border-gray-300 dark:border-gray-800 rounded-full transition-colors" title="Export to CSV">
                <Download size={20} />
              </button>
              
              {/* NEW: Theme Toggle Button */}
              <button 
                onClick={() => setIsDarkMode(!isDarkMode)} 
                className="p-2 text-gray-500 dark:text-gray-400 hover:text-black dark:hover:text-white bg-white dark:bg-[#141414] border border-gray-300 dark:border-gray-800 rounded-full transition-colors"
              >
                {isDarkMode ? <Sun size={20} /> : <Moon size={20} />}
              </button>
            </div>
          </div>

          <div className="flex flex-col sm:flex-row gap-4">
            <input 
                  type="text" 
                  placeholder="Search job or company..." // <-- Changed here
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                className="flex-1 p-3 bg-white dark:bg-[#141414] border border-gray-300 dark:border-gray-800 text-gray-900 dark:text-white rounded-xl focus:ring-2 focus:ring-gray-400 dark:focus:ring-gray-600 outline-none transition-colors"
            />
            <select
              value={filterPriority}
              onChange={(e) => setFilterPriority(e.target.value as 'All' | Priority)}
              className="p-3 bg-white dark:bg-[#141414] border border-gray-300 dark:border-gray-800 text-gray-900 dark:text-white rounded-xl focus:ring-2 focus:ring-gray-400 dark:focus:ring-gray-600 outline-none cursor-pointer transition-colors"
            >
              <option value="All">All Priorities</option>
              <option value="High">High Priority</option>
              <option value="Medium">Medium Priority</option>
              <option value="Low">Low Priority</option>
            </select>
            <button 
                onClick={() => setIsModalOpen(true)}
                    className="bg-black dark:bg-white text-white dark:text-black hover:bg-gray-800 dark:hover:bg-gray-200 px-6 py-3 rounded-xl font-bold transition-colors whitespace-nowrap"
                      >
                + Add Job {/* <-- Changed here */}
            </button>
          </div>
        </header>

        <DndContext sensors={sensors} collisionDetection={closestCorners} onDragStart={handleDragStart} onDragOver={handleDragOver} onDragEnd={handleDragEnd}>
          <div className="flex flex-col md:flex-row gap-6 md:overflow-x-auto pb-4 items-start">
            {COLUMNS.map((status) => (
                <Column
                       key={status}
                        id={status}
                        title={status}
                        jobs={filteredJobs.filter((job) => job.status === status)}
                        onDeleteJob={deleteJob} 
                        onAddClick={() => setIsModalOpen(true)}
                />
            ))}
          </div>
          <DragOverlay>
            {activeJob ? <JobCard job={activeJob} isOverlay /> : null}
          </DragOverlay>
        </DndContext>
        
        <footer className="mt-20 text-center text-xs text-gray-500 dark:text-gray-600">
          Designed & Created by Ronin | Icons by Lucide
        </footer>
      </div>

      {isModalOpen && <AddJobModal onClose={() => setIsModalOpen(false)} onAdd={(newJob) => setJobs([...jobs, newJob])} />}
    </div>
  );
}